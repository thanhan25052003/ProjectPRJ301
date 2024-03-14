/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

// Import các package và class cần thiết
import dal.implement.CategoryDAO;
import dal.implement.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import model.Product;

/**
 * ProductAdminServlet xử lý các yêu cầu quản lý sản phẩm từ admin.
 */
@MultipartConfig // Chỉ ra rằng Servlet này hỗ trợ upload file
public class ProductAdminServlet extends HttpServlet {

    // Khởi tạo DAOs để tương tác với cơ sở dữ liệu
    ProductDAO pdao = new ProductDAO();
    CategoryDAO cateDAO = new CategoryDAO();

    // Xử lý yêu cầu HTTP GET và POST mặc định
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Cơ bản in ra một trang HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductAdminServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductAdminServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // Gọi lại processRequest
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đặt encoding để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Lấy action từ request để xác định hành động: thêm, xoá, sửa sản phẩm
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "add":
                addProduct(request); // Thêm sản phẩm mới
                break;
            case "delete":
                deleteProduct(request); // Xoá sản phẩm
                break;
            case "edit":
                editProduct(request); // Sửa thông tin sản phẩm
                break;
            default:
            // Nếu không có hành động nào được xác định
        }
        response.sendRedirect("dashboard"); // Chuyển hướng về trang dashboard sau khi xử lý
    }

    // Phương thức thêm sản phẩm mới
    private void addProduct(HttpServletRequest request) throws IOException, ServletException {
        // Lấy thông tin sản phẩm từ form
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        // Xử lý upload ảnh
        Part part = request.getPart("image");
        String imagePath = uploadImage(request, part);

        // Tạo đối tượng Product mới và thêm vào cơ sở dữ liệu
        Product product = Product.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .categoryId(categoryId)
                .description(description)
                .image(imagePath)
                .build();
        pdao.insert(product);
    }

    // Phương thức xoá sản phẩm
    private void deleteProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        pdao.deleteById(id);
    }

    // Phương thức sửa sản phẩm
    private void editProduct(HttpServletRequest request) throws IOException, ServletException {
        // Lấy thông tin sản phẩm và thực hiện cập nhật
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        // Xử lý upload ảnh mới, nếu có
        Part part = request.getPart("image");
        String currentImagePath = request.getParameter("currentImage");
        String imagePath = uploadImageWithCurrent(request, part, currentImagePath);

        // Cập nhật đối tượng Product và lưu vào cơ sở dữ liệu
        Product product = Product.builder()
                .id(id)
                .name(name)
                .quantity(quantity)
                .price(price)
                .description(description)
                .categoryId(categoryId)
                .image(imagePath)
                .build();
        pdao.update(product);
    }

    // Hỗ trợ upload ảnh và trả về đường dẫn ảnh
    private String uploadImage(HttpServletRequest request, Part part) throws IOException {
        String imagePath = null;
        if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().trim().isEmpty()) {
            String path = request.getServletContext().getRealPath("/images");
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File image = new File(dir, part.getSubmittedFileName());
            part.write(image.getAbsolutePath());
            imagePath = request.getContextPath() + "/images/" + image.getName();
        }
        return imagePath;
    }

    // Hỗ trợ upload ảnh mới nếu có, nếu không sử dụng ảnh hiện tại
    private String uploadImageWithCurrent(HttpServletRequest request, Part part, String currentImagePath) throws IOException {
        String newPath = uploadImage(request, part);
        return newPath != null ? newPath : currentImagePath;
    }
}
