/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

// Import các class cần thiết từ các gói khác
import constant.CommonConst;
import dal.GenericDAO;
import model.Product;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Lớp ProductDAO mở rộng từ GenericDAO và được dùng để tương tác với bảng
 * Product trong cơ sở dữ liệu.
 */
public class ProductDAO extends GenericDAO<Product> {

    @Override
    public List<Product> findAll() {
        // Phương thức truy vấn tất cả sản phẩm từ cơ sở dữ liệu
        return queryGenericDAO(Product.class);
    }

    @Override
    public int insert(Product t) {
        // Phương thức chèn một sản phẩm mới vào cơ sở dữ liệu
        return insertGenericDAO(t);
    }

    public Product findById(Product product) {
        // Câu lệnh SQL để tìm sản phẩm theo ID
        String sql = "SELECT [id], [name], [image], [quantity], [price], [description], [categoryId] FROM [dbo].[Product] WHERE id = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("id", product.getId());
        List<Product> list = queryGenericDAO(Product.class, sql, parameterMap);
        // Kiểm tra nếu danh sách trả về không có sản phẩm nào thì trả về null, ngược lại trả về sản phẩm đầu tiên
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Product> findByCategory(String categoryId, int page) {
        // Câu lệnh SQL để truy vấn sản phẩm theo danh mục và phân trang
        String sql = "SELECT * FROM [Product] WHERE categoryId = ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("categoryId", categoryId);
        parameterMap.put("offset", (page - 1) * CommonConst.RECORD_PER_PAGE);
        parameterMap.put("fetch", CommonConst.RECORD_PER_PAGE);
        return queryGenericDAO(Product.class, sql, parameterMap);
    }

    public List<Product> findByName(String keyword, int page) {
        // Tương tự như findByCategory nhưng tìm kiếm sản phẩm theo từ khóa trong tên
        String sql = "SELECT * FROM [Product] WHERE [name] LIKE ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        parameterMap.put("offset", (page - 1) * CommonConst.RECORD_PER_PAGE);
        parameterMap.put("fetch", CommonConst.RECORD_PER_PAGE);
        return queryGenericDAO(Product.class, sql, parameterMap);
    }

    public int findTotalRecordByCategory(String categoryId) {
        // Câu lệnh SQL để đếm tổng số sản phẩm trong một danh mục
        String sql = "SELECT count(*) FROM Product WHERE categoryId = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("categoryId", categoryId);
        return findTotalRecordGenericDAO(Product.class, sql, parameterMap);
    }

    public int findTotalRecordByName(String keyword) {
        // Đếm tổng số sản phẩm theo từ khóa tìm kiếm
        String sql = "SELECT count(*) FROM Product WHERE [name] LIKE ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        return findTotalRecordGenericDAO(Product.class, sql, parameterMap);
    }

    public int findTotalRecord() {
        // Đếm tổng số sản phẩm trong cơ sở dữ liệu
        String sql = "SELECT count(*) FROM Product";
        parameterMap = new LinkedHashMap<>();
        return findTotalRecordGenericDAO(Product.class, sql, parameterMap);
    }

    public List<Product> findByPage(int page) {
        // Truy vấn sản phẩm và phân trang
        String sql = "SELECT * FROM Product ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("offset", (page - 1) * CommonConst.RECORD_PER_PAGE);
        parameterMap.put("fetch", CommonConst.RECORD_PER_PAGE);
        return queryGenericDAO(Product.class, sql, parameterMap);
    }

    public void deleteById(int id) {
        // Câu lệnh SQL để xoá một sản phẩm theo ID
        String sql = "DELETE FROM [dbo].[Product] WHERE [id] = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("id", id);
        deleteGenericDAO(sql, parameterMap);
    }

    public void update(Product product) {
        // Câu lệnh SQL để cập nhật thông tin sản phẩm
        String sql = "UPDATE [dbo].[Product] SET [name] = ?, [image] = ?, [quantity] = ?, [price] = ?, [description] = ?, [categoryId] = ? WHERE id = ?";
        parameterMap = new LinkedHashMap<>();
        // Đặt giá trị cho các tham số từ đối tượng product
        parameterMap.put("name", product.getName());
        parameterMap.put("image", product.getImage());
        parameterMap.put("quantity", product.getQuantity());
        parameterMap.put("price", product.getPrice());
        parameterMap.put("description", product.getDescription());
        parameterMap.put("categoryId", product.getCategoryId());
        parameterMap.put("id", product.getId());
        updateGenericDAO(sql, parameterMap);
    }

}
