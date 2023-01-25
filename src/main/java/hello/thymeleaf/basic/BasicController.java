package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data","Hello <b>Spring</b>");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data","Hello <b>Spring</b>");
        return "basic/text-basic";
    }

    /*SpringEL 표현식, JspEL과 비슷*/
    @GetMapping("variable")
    public String variable(Model model){
       User userA = new User("userA", 10);
       User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA",userA);
        map.put("userB",userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    /** 세션과 param */
    @GetMapping("basic-objects")
    public String basicObject(HttpSession session){
        //세션 : 유저가 웹 브라우저를 종료전까지 데이터가 유지된다.
        session.setAttribute("sessionData","Hello Session");
        return "basic/basic-objects";
    }

    /** 자바8 날짜 */
    @GetMapping("date")
    public String date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";

    }

    /** URL 링크*/
    @GetMapping("link")
    public String link(Model model){
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");
        return "basic/link";
    }

    /** 리터럴 */
    @GetMapping("literal")
    public String literal(Model model){
        model.addAttribute("data", "Spring");
        return "basic/literal";
    }

    /** 연산
     * 타임리프 연산은 자바와 크게 다르지 않다. HTML안에서 사용하기 때문에 HTML 엔티티를 사용하는 부분만 주의하자!
     * */
    @GetMapping("operation")
    public String operation(Model model){
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }

    /** 속성 설정 */
    @GetMapping("attribute")
    public String attribute(){
        return "basic/attribute";
    }

    /** 반복 */
    @GetMapping("/each")
    public String each(Model model){

        addUsers(model); //좀 신기! 간단하네 오히려 더

        return "basic/each";
    }

    /** 조건부 평가 */
    @GetMapping("/condition")
    public String condition(Model model){
        addUsers(model);

        return "basic/condition";
    }

    /** 주석(html주석, thymeleaf주석) */
    @GetMapping("/comments")
    public String comments(Model model){
        model.addAttribute("data","Spring");
        return "basic/comments";
    }

    /** 블록(타임리프는 주로 속성으로 동작하지, 태그로 동작하지 않지만 유일한 자체 태그이다) */
    @GetMapping("block")
    public String block(Model model){
        addUsers(model);
        return "basic/block";
    }

    /** 자바스크립트 인라인 */
    @GetMapping("javascript")
    public String javascript(Model model){

        model.addAttribute("user", new User("UserLone",10));
        addUsers(model);

        return "basic/javascript";
    }

    private void addUsers(Model model){
        List<User> list = new ArrayList<>();
        list.add(new User("UserA", 10));
        list.add(new User("UserB", 20));
        list.add(new User("UserC", 30));

        model.addAttribute("users", list);
    }

    //굉장히 단순한 스프링 빈 등록
    @Component("helloBean")
    static class HelloBean{
        public String hello(String data){
            return "Hello "+data;
        }
    }

    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age){
            this.username = username;
            this.age = age;
        }
    }

}
