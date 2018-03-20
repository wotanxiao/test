package com.jxduhai.manager.controller;

import com.jxduhai.manager.pojo.Item;
import com.jxduhai.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/****
 *@author yxw
 *@date 2018/3/18
 * 编写Restful风格编写对数据进行增删改查接口
 */

@Controller
@RequestMapping("/item/interface")
public class ItemInterfaceController {

    @Autowired
    private ItemService itemService;

    /**查询商品*/
    @ResponseBody
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseEntity<Item> queryById(@PathVariable(value = "id")Long id){
        try {
            //查询成功成功时,给其设置状态码返回
            Item item = itemService.queryById(id);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询为找到数据,返回给客户null值,并标识状态码为404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    /**
     * 添加商品
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item){
        try {
            //商品添加成功
            itemService.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    /**
     * 更新商品
     * */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item){
        try {
            itemService.updateByIdSelective(item);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    /**
     * 根据Id删除商品
     * */
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id")Long id){
        try {
            itemService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
