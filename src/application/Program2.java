package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args){
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== Test 1: findById ===");
        Department dep = departmentDao.findById(1);
        System.out.println(dep);

        System.out.println("\n=== Test 2: Department findAll ===");
        List<Department> departmentList = departmentDao.findAll();
        for (Department department:
             departmentList) {
            System.out.println(department);
        }

        System.out.println("\n=== Test 3: Department insert ===");
        Department newDep = new Department(7, "Games");
        departmentDao.insert(newDep);
        System.out.println(newDep.getId());

        System.out.println("\n=== Test 4: Department update ===");
        dep = departmentDao.findById(10);
        dep.setName("Music");
        departmentDao.update(dep);
        System.out.println("Updated completed");


    }
}
