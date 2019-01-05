package com.boe.customer.controller;/*
 *ClassName:UserController
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/7 20:07
 *@author:tang@qq.com
 */
import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.springboot.model.PageVo;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import com.bjpowernode.tool.FastFDS;
import org.csource.fastdfs.StorageClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Reference
    private UserService userService;
    //查询所有信息显示列表 currentPage totalRows totalPage
    @RequestMapping(value = "/query/alluser")
    public String queryInfo(Model model,@RequestParam(value = "currentPage",required = false) Integer currentPage){
         if (currentPage == null){
             currentPage=1;
         }
         // 计算总条数，总页数  每页显示5条
        Integer pageSize = 5;
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("currentPage",(currentPage -1)*pageSize);
        PageVo<User> vo =  userService.queryUser(map);
        Integer totalPage = Math.toIntExact(vo.getTotalPage() / pageSize);
        if (vo.getTotalPage() % pageSize > 0){
            totalPage = totalPage +1;
        }
        model.addAttribute("userList",vo.getPageList());
        model.addAttribute("totalRows",vo.getTotalPage());
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("currentPage",currentPage);
        return "index";
    }
    //增加用户信息
    @RequestMapping(value = "/add/user")
    public String add(){
        return  "add";
    }
    @RequestMapping(value = "/user")
    public String showInfo(@RequestParam(value = "name",required = true) String name,
                                 @RequestParam(value = "phone",required = true) String phone,
                                 @RequestParam(value = "address",required = true) String address,
                                 @RequestParam(value = "email",required = true) String email){
        User user = new User();
        user.setNick(name);
        user.setPhone(phone);
        user.setAddress(address);
        user.setEmail(email);

            int count = userService.insertUser(user);
            if (count < 0){
                System.out.println("添加失败");
            }

        return "redirect:/query/alluser";
    }
    //修改用户信息/update/user
    @RequestMapping(value = "/update/user")
    public String update(Model model,@RequestParam(value = "id",required = true) Integer id){
        User user = userService.queryUserById(id);
        model.addAttribute("user",user);
        return "two";
    }
    @RequestMapping(value = "/updateuser")
    public String updateuser(@RequestParam(value = "name",required = true) String name,
                           @RequestParam(value = "id",required = true) Integer id,
                           @RequestParam(value = "phone",required = true) String phone,
                           @RequestParam(value = "address",required = true) String address,
                           @RequestParam(value = "email",required = true) String email){
        User user = new User();
        user.setNick(name);
        user.setPhone(phone);
        user.setAddress(address);
        user.setEmail(email);
        user.setId(id);
        int count = userService.updateUserById(user);
        return "redirect:/query/alluser";
    }
    //删除用户信息
    @RequestMapping(value = "/delete/user")
    public String delete(@RequestParam(value = "id",required = true) Integer id){
        int coutn = userService.deleteUser(id);

        return "redirect:/query/alluser";
    }
    /*文件上传页面跳转
    * */
    @RequestMapping(value = "/uploading/file")
    public String uploading(Model model,@RequestParam(value = "id",required = true) Integer id){
        model.addAttribute("id",id);
        return "upload";
    }
    /**
     * 文件上传方法
    * */
    @RequestMapping(value = "/fastdfs/uploadfile")
    public @ResponseBody  String fastdfsUpload(@RequestParam(value = "id",required = true) Integer id ,
                         @RequestParam(value = "inputfile",required = true) MultipartFile inputfile){
        int result = 1;

        //采用fastdfs，将文件上传至fastdfs 分布式的文件系统服务器上面
        //获取原始文件名 获取到这个文件名的后缀，比如aaa.pdf 后缀就是pdf
        String extFileName = inputfile.getOriginalFilename().substring(inputfile.getOriginalFilename().lastIndexOf(".")+1);
        try {
            //文件的字节数组
            String[] strArray = FastFDS.uploadFile(inputfile.getBytes(), extFileName);
            //来进行判断 取出数组里面的元素，把值赋值给对应的表单里面的值
            if ( null != strArray && strArray.length == 2){
                //更新此表单里面的group值，和 上传后文件的名字
                User user = new User();
                user.setId(id);
                user.setAddress(strArray[0]);
                user.setEmail(strArray[1]);
                int count = userService.updateUserById(user);
                if (count > 0){
                    System.out.println("上传Ok");
                    result = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            FastFDS.closeFastDfs();
        }
        return "<script>window.parent.uploadok('"+result+"')</script>";
    }

    /**
     * 文件下载
    * */
    @RequestMapping(value = "/download/file")
    public ResponseEntity<byte[]>  download(@RequestParam(value = "id",required = true) Integer id){

        //查出数据表中的信息
        User user = userService.queryUserById(id);
        String extFileName = user.getEmail().substring(user.getEmail().lastIndexOf("."));
        //调用工具总的下载方法 文件就在这个字节数组里面的
        byte[] bytes = FastFDS.downloadFile(user.getAddress(), user.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        headers.setContentDispositionFormData("attachment",System.currentTimeMillis()+extFileName);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
        return responseEntity;
    }

  /**
  * 文件的删除
  * */
    @RequestMapping(value = "/deletefile/user")
    public String deleteFile(Model model,@RequestParam(value = "id",required = true) Integer id){

        int delete = 1;
        //0表示删除成功，其他的都是失败
        User user = userService.queryUserById(id);
        int i = FastFDS.deleteFile(user.getAddress(), user.getEmail());
        if (i == 0){
            //跟新数据库表中的信息
            User updateUser = new User();
            updateUser.setId(id);
            updateUser.setAddress("");
            updateUser.setEmail("");
            int count = userService.updateUserById(updateUser);
            if (count > 0){
                delete = 0;
                model.addAttribute("delete",delete);
            }
        }
        return "redirect:/query/alluser";
    }


}
