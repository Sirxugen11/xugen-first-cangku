package xg.demo.test.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*自定义用于分页的类*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PmPage {
    //初始页
    private Integer current;
    //每页的大小
    private Integer size;
    //总数据条数
    private Integer total;
    //总页数
    private Integer pages;


    //最后得到的分页后的数据(因为煞笔前端写死了取json格式的records属性里的值，所以需要加这个名字的属性用来存放分页查询后的数据)
    private Object records;

    /*public Integer getPages() {
        this.pages = total%size==0 ? total/size : total/size+1;
        return total%size==0 ? total/size : total/size+1;
    }*/
}
