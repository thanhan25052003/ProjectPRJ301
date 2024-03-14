/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

// Import các thư viện cần thiết để xử lý servlet và I/O
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Khai báo lớp DashboardUserServlet kế thừa từ HttpServlet để xử lý các yêu cầu HTTP đến từ người dùng
public class DashboardUserServlet extends HttpServlet {

    // Override phương thức doGet để xử lý các yêu cầu GET đến servlet này
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Sử dụng RequestDispatcher để chuyển tiếp yêu cầu đến trang JSP của dashboard người dùng
        // "view/user/dashboard/dashboard.jsp" là đường dẫn đến file JSP trong ứng dụng web của bạn
        request.getRequestDispatcher("view/user/dashboard/dashboard.jsp").forward(request, response);
    }

    // Override phương thức doPost để xử lý các yêu cầu POST đến servlet này
    // Trong ví dụ này, phương thức doPost không thực hiện gì cả, nhưng nó có thể được sử dụng
    // để xử lý dữ liệu gửi từ form, chẳng hạn như cập nhật thông tin người dùng, v.v.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Có thể thêm code để xử lý dữ liệu form gửi đến thông qua phương thức POST ở đây
    }

}
