/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import dal.GenericDAO; // Kế thừa lớp GenericDAO để sử dụng các phương thức truy cập dữ liệu chung
import model.Account; // Sử dụng model Account cho việc mapping dữ liệu
import java.util.LinkedHashMap; // Import LinkedHashMap để lưu các tham số truy vấn
import java.util.List; // Sử dụng List từ java.util để chứa và quản lý danh sách các đối tượng Account

/**
 * Lớp AccountDAO dùng để tương tác cụ thể với bảng Account trong cơ sở dữ liệu
 */
public class AccountDAO extends GenericDAO<Account> { // Mở rộng từ GenericDAO với kiểu là Account

    @Override
    public List<Account> findAll() {
        // Lấy tất cả các bản ghi từ bảng Account
        return queryGenericDAO(Account.class);
    }

    @Override
    public int insert(Account t) {
        // Câu lệnh SQL để chèn một bản ghi mới vào bảng Account
        String sql = "INSERT INTO [dbo].[Account] ([username], [password], [email], [address], [roleId]) VALUES (?, ?, ?, ?, 2)";
        parameterMap = new LinkedHashMap<>(); // Khởi tạo LinkedHashMap để lưu các tham số truy vấn
        // Đặt giá trị cho các tham số truy vấn dựa trên đối tượng Account được cung cấp
        parameterMap.put("username", t.getUsername());
        parameterMap.put("password", t.getPassword());
        parameterMap.put("email", t.getEmail());
        parameterMap.put("address", t.getAddress());
        // Gọi phương thức insertGenericDAO từ lớp cha để thực hiện chèn dữ liệu, trả về ID của bản ghi mới
        return insertGenericDAO(sql, parameterMap);
    }

    public Account findByUsernameAndPass(Account account) {
        // Câu lệnh SQL để tìm một Account dựa trên username và password
        String sql = "SELECT * FROM [dbo].[Account] where username = ? and password = ?";
        parameterMap = new LinkedHashMap<>(); // Khởi tạo LinkedHashMap để lưu các tham số truy vấn
        // Đặt giá trị cho các tham số truy vấn
        parameterMap.put("username", account.getUsername());
        parameterMap.put("password", account.getPassword());
        // Gọi phương thức queryGenericDAO để thực hiện truy vấn, trả về danh sách Account
        List<Account> list = queryGenericDAO(Account.class, sql, parameterMap);
        // Kiểm tra danh sách trả về, nếu rỗng trả về null, ngược lại trả về Account đầu tiên trong danh sách
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean checkUsernameExist(Account account) {
        // Câu lệnh SQL để kiểm tra xem username đã tồn tại trong bảng Account hay chưa
        String sql = "SELECT * FROM [dbo].[Account] where username = ?";
        parameterMap = new LinkedHashMap<>(); // Khởi tạo LinkedHashMap để lưu tham số truy vấn
        parameterMap.put("username", account.getUsername()); // Đặt giá trị cho tham số truy vấn
        // Gọi phương thức queryGenericDAO để thực hiện truy vấn, kiểm tra danh sách trả về
        // Nếu danh sách không rỗng, tức là username đã tồn tại, trả về true, ngược lại trả về false
        return !queryGenericDAO(Account.class, sql, parameterMap).isEmpty();
    }

}
