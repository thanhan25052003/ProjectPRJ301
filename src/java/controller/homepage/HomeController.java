/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homepage;

// Import các thư viện và class cần thiết
import constant.CommonConst;
import dal.implement.CategoryDAO;
import dal.implement.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Category;
import model.PageControl;
import model.Product;

/**
 * HomeController kế thừa từ HttpServlet, quản lý yêu cầu liên quan đến trang
 * chủ
 */
public class HomeController extends HttpServlet {

    // Khởi tạo DAOs để tương tác với database
    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDao = new CategoryDAO();

    // Xử lý yêu cầu GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PageControl pageControl = new PageControl();
        List<Product> listProduct = findProductDoGet(request, pageControl); // Lấy danh sách sản phẩm
        List<Category> listCategory = categoryDao.findAll(); // Lấy danh sách danh mục

        // Lưu danh sách sản phẩm và danh mục vào session
        HttpSession session = request.getSession();
        session.setAttribute(CommonConst.SESSION_PRODUCT, listProduct);
        session.setAttribute(CommonConst.SESSION_CATEGORY, listCategory);

        // Đặt thông tin về phân trang vào request và chuyển tiếp đến trang JSP
        request.setAttribute("pageControl", pageControl);
        request.getRequestDispatcher("view/homepage/home.jsp").forward(request, response);
    }

    // Xử lý yêu cầu POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("home"); // Chuyển hướng đến trang chủ khi nhận yêu cầu POST
    }

    // Trả về mô tả ngắn gọn của servlet
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    // Hàm tìm kiếm sản phẩm dựa trên yêu cầu GET, xử lý phân trang và tìm kiếm
    private List<Product> findProductDoGet(HttpServletRequest request, PageControl pagecontrol) {
        // Xử lý số trang được yêu cầu
        String pageRaw = request.getParameter("page");
        int page;
        try {
            page = Integer.parseInt(pageRaw);
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        // Xác định hành động tìm kiếm
        String actionSearch = request.getParameter("search") == null
                ? "default"
                : request.getParameter("search");
        List<Product> listProduct;
        String requestURL = request.getRequestURL().toString();
        int totalRecord;

        // Xử lý tìm kiếm dựa trên hành động: theo danh mục, theo tên hoặc mặc định
        switch (actionSearch) {
            case "category":
                // Tìm kiếm theo danh mục
                String categoryId = request.getParameter("categoryId");
                totalRecord = productDAO.findTotalRecordByCategory(categoryId);
                listProduct = productDAO.findByCategory(categoryId, page);
                pagecontrol.setUrlPattern(requestURL + "?search=category&categoryId=" + categoryId + "&");
                break;
            case "searchByName":
                // Tìm kiếm theo tên sản phẩm
                String keyword = request.getParameter("keyword");
                totalRecord = productDAO.findTotalRecordByName(keyword);
                listProduct = productDAO.findByName(keyword, page);
                pagecontrol.setUrlPattern(requestURL + "?search=searchByName&keyword=" + keyword + "&");
                break;
            default:
                // Lấy tất cả sản phẩm
                totalRecord = productDAO.findTotalRecord();
                listProduct = productDAO.findByPage(page);
                pagecontrol.setUrlPattern(requestURL + "?");
        }

        // Tính toán tổng số trang
        int totalPage = (totalRecord % CommonConst.RECORD_PER_PAGE) == 0
                ? (totalRecord / CommonConst.RECORD_PER_PAGE)
                : (totalRecord / CommonConst.RECORD_PER_PAGE) + 1;

        // Thiết lập thông tin phân trang
        pagecontrol.setPage(page);
        pagecontrol.setTotalPage(totalPage);
        pagecontrol.setTotalRecord(totalRecord);

        return listProduct;
    }

}
