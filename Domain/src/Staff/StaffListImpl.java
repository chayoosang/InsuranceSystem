package Staff;

import java.util.ArrayList;

public class StaffListImpl implements StaffList{

    ArrayList<Staff> staffList;

    public StaffListImpl() {
        this.staffList = new ArrayList<>();
    }

    @Override
    public boolean add(Staff staff) {
        if (this.staffList.add(staff)) {
            return true;
        } else
            return false;
    }

    @Override
    public boolean delete(int staffId) {
        if (this.staffList.add(this.get(staffId))) {
            return true;
        } else
            return false;
    }

    @Override
    public Staff get(int staffId) {
        for (Staff staff : staffList) {
            if (staff.getId() == staffId) {
                return staff;
            }
        }
        return null;
    }
}