/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homepage;

// Import các thư viện và class cần thiết
import dal.implement.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

/**
 * ProductDetailsController kế thừa từ HttpServlet, chịu trách nhiệm xử lý chi
 * tiết sản phẩm
 */
public class ProductDetailsController extends HttpServlet {

    // Khởi tạo ProductDAO để tương tác với cơ sở dữ liệu sản phẩm
    ProductDAO productDAO = new ProductDAO();

    /**
     * Xử lý các yêu cầu HTTP GET và POST.
     *
     * @param request đối tượng yêu cầu servlet
     * @param response đối tượng phản hồi servlet
     * @throws ServletException nếu xảy ra lỗi cụ thể của servlet
     * @throws IOException nếu xảy ra lỗi I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Mẫu HTML được in ra để hiển thị trên trình duyệt
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductDetailsController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailsController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Xử lý yêu cầu HTTP GET.
     *
     * @param request đối tượng yêu cầu servlet
     * @param response đối tượng phản hồi servlet
     * @throws ServletException nếu xảy ra lỗi cụ thể của servlet
     * @throws IOException nếu xảy ra lỗi I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID của sản phẩm từ yêu cầu
        int id = Integer.parseInt(request.getParameter("id"));
        // Tạo một đối tượng Product với ID đã cho
        Product product = Product.builder()
                .id(id)
                .build();
        // Tìm sản phẩm trong cơ sở dữ liệu bằng cách sử dụng DAO
        Product productFoundById = productDAO.findById(product);
        // Đặt sản phẩm tìm được vào request để có thể truy cập từ JSP
        request.setAttribute("product", productFoundById);
        // Chuyển tiếp yêu cầu và phản hồi đến trang JSP chi tiết sản phẩm
        request.getRequestDispatcher("view/homepage/product-details.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu HTTP POST.
     *
     * @param request đối tượng yêu cầu servlet
     * @param response đối tượng phản hồi servlet
     * @throws ServletException nếu xảy ra lỗi cụ thể của servlet
     * @throws IOException nếu xảy ra lỗi I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return một String chứa mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
