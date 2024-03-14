/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.implement;

import dal.GenericDAO; // Import lớp GenericDAO để sử dụng các phương thức truy cập dữ liệu chung
import model.Role; // Import model Role để sử dụng trong các phương thức này
import java.util.List; // Import List từ java.util để chứa và quản lý danh sách các đối tượng Role

/**
 * Lớp RoleDAO kế thừa từ GenericDAO, được sử dụng để tương tác với bảng Role
 * trong cơ sở dữ liệu
 */
public class RoleDAO extends GenericDAO<Role> { // Kế thừa từ GenericDAO với kiểu dữ liệu là Role

    @Override
    public List<Role> findAll() {
        // Phương thức truy vấn tất cả các bản ghi từ bảng Role và trả về danh sách các đối tượng Role
        return queryGenericDAO(Role.class);
    }

    @Override
    public int insert(Role t) {
        // Phương thức này không được hỗ trợ trong RoleDAO.
        throw new UnsupportedOperationException("Not supported yet."); // Ném ra một ngoại lệ UnsupportedOperationException
    }

}
