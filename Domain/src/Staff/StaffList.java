package Staff;

public interface StaffList {

    public boolean add(Staff staff);

    public boolean delete(int staffId);

    public Staff get(int staffId);
    //public void update();
}