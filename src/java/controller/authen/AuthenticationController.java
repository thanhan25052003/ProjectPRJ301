/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authen;

// Import các thư viện và class cần thiết
import constant.CommonConst;
import dal.implement.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

/**
 * AuthenticationController kế thừa từ HttpServlet, xử lý các yêu cầu đăng nhập,
 * đăng ký và đăng xuất.
 */
public class AuthenticationController extends HttpServlet {

    // Khởi tạo AccountDAO để tương tác với cơ sở dữ liệu tài khoản
    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số action từ request để xác định hành động: đăng nhập, đăng xuất, hoặc đăng ký
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        // Dựa vào action để định tuyến URL cần chuyển đến
        String url;
        switch (action) {
            case "login":
                url = "view/authen/login.jsp"; // URL trang đăng nhập
                break;
            case "log-out":
                url = logOut(request, response); // Hàm xử lý đăng xuất
                break;
            case "sign-up":
                url = "view/authen/register.jsp"; // URL trang đăng ký
                break;
            default:
                url = "home"; // URL trang chủ
        }
        // Chuyển trang dựa trên URL đã định tuyến
        request.getRequestDispatcher(url).forward(request, response);
    }

    private String logOut(HttpServletRequest request, HttpServletResponse response) {
        // Xoá thông tin tài khoản khỏi session để đăng xuất
        request.getSession().removeAttribute(CommonConst.SESSION_ACCOUNT);
        return "home"; // Trở về trang chủ sau khi đăng xuất
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lại lấy tham số action từ request để xác định hành động
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        // Dựa vào action để xử lý request
        String url;
        switch (action) {
            case "login":
                url = loginDoPost(request, response); // Hàm xử lý đăng nhập
                break;
            case "sign-up":
                url = signUp(request, response); // Hàm xử lý đăng ký
                break;
            default:
                url = "home"; // Nếu không xác định được action, trở về trang chủ
        }
        // Chuyển trang dựa trên URL đã định tuyến
        request.getRequestDispatcher(url).forward(request, response);
    }

    private String loginDoPost(HttpServletRequest request, HttpServletResponse response) {
        // Lấy thông tin username và password từ form đăng nhập
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Tạo đối tượng Account dựa trên thông tin nhận được
        Account account = Account.builder().username(username).password(password).build();
        // Kiểm tra thông tin đăng nhập trong cơ sở dữ liệu
        Account accFoundByUsernamePass = accountDAO.findByUsernameAndPass(account);
        if (accFoundByUsernamePass != null) {
            // Nếu đăng nhập thành công, lưu thông tin tài khoản vào session và chuyển đến trang chủ
            request.getSession().setAttribute(CommonConst.SESSION_ACCOUNT, accFoundByUsernamePass);
            return "home";
        } else {
            // Nếu đăng nhập thất bại, trả về trang đăng nhập và hiển thị thông báo lỗi
            request.setAttribute("error", "Username or password incorrect!!");
            return "view/authen/login.jsp";
        }
    }

    private String signUp(HttpServletRequest request, HttpServletResponse response) {
        // Lấy thông tin từ form đăng ký
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Tạo đối tượng Account mới
        Account account = Account.builder().username(username).password(password).build();
        // Kiểm tra username đã tồn tại trong cơ sở dữ liệu hay chưa
        boolean isExistUsername = accountDAO.checkUsernameExist(account);
        if (isExistUsername) {
            // Nếu username đã tồn tại, trả về trang đăng ký và hiển thị thông báo lỗi
            request.setAttribute("error", "Username exist !!");
            return "view/authen/register.jsp";
        } else {
            // Nếu username chưa tồn tại, thêm tài khoản mới vào cơ sở dữ liệu và chuyển đến trang chủ
            accountDAO.insert(account);
            return "home";
        }
    }
}
