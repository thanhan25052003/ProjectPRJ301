/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

// Import các thư viện và class cần thiết
import constant.CommonConst; // Import các hằng số chung
import dal.implement.CategoryDAO; // Import DAO để tương tác với bảng Category trong cơ sở dữ liệu
import dal.implement.ProductDAO; // Import DAO để tương tác với bảng Product trong cơ sở dữ liệu
import java.io.IOException;
import jakarta.servlet.ServletException; // Import để xử lý ngoại lệ ServletException
import jakarta.servlet.http.HttpServlet; // Import HttpServlet làm cơ sở cho servlet
import jakarta.servlet.http.HttpServletRequest; // Import để nhận request từ client
import jakarta.servlet.http.HttpServletResponse; // Import để gửi response về cho client
import jakarta.servlet.http.HttpSession; // Import để làm việc với session
import java.util.List; // Import để sử dụng List
import model.Category; // Import model Category
import model.Product; // Import model Product

/**
 * DashboardServlet xử lý các yêu cầu đến trang dashboard của admin.
 */
public class DashboardServlet extends HttpServlet {

    // Khởi tạo DAO để tương tác với cơ sở dữ liệu
    ProductDAO dao = new ProductDAO();
    CategoryDAO categoryDao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy đối tượng session hiện tại
        HttpSession session = request.getSession();
        // Truy vấn tất cả sản phẩm từ cơ sở dữ liệu
        List<Product> listProduct = dao.findAll();
        // Truy vấn tất cả danh mục từ cơ sở dữ liệu
        List<Category> listCategory = categoryDao.findAll();

        // Lưu danh sách sản phẩm và danh mục vào session
        session.setAttribute(CommonConst.SESSION_PRODUCT, listProduct);
        session.setAttribute(CommonConst.SESSION_CATEGORY, listCategory);

        // Chuyển hướng yêu cầu đến trang dashboard của admin
        request.getRequestDispatcher("../view/admin/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Phương thức doPost có thể được sử dụng để xử lý dữ liệu gửi từ form nếu cần
    }

}
