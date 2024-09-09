import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Apartment {
    private int id;
    private String number;
    private double area;
    private int floor;
    private int rooms;
    private String street;
    private String buildingType;
    private int exploitationTerm;

    // Конструкторы
    public Apartment(int id, String number, double area, int floor, int rooms, String street, String buildingType, int exploitationTerm) {
        this.id = id;
        this.number = number;
        this.area = area;
        this.floor = floor;
        this.rooms = rooms;
        this.street = street;
        this.buildingType = buildingType;
        this.exploitationTerm = exploitationTerm;
    }

    // Геттеры
    public int getRooms() {
        return rooms;
    }

    public double getArea() {
        return area;
    }

    public int getFloor() {
        return floor;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", area=" + area +
                ", floor=" + floor +
                ", rooms=" + rooms +
                ", street='" + street + '\'' +
                ", buildingType='" + buildingType + '\'' +
                ", exploitationTerm=" + exploitationTerm +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}

class ApartmentManager {
    private List<Apartment> apartments;

    public ApartmentManager() {
        apartments = new ArrayList<>();
    }

    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
    }

    public void removeApartment(int id) {
        apartments.removeIf(apartment -> apartment.getId() == id);
    }

    public List<Apartment> filterByRooms(int numberOfRooms) {
        return apartments.stream()
                .filter(apartment -> apartment.getRooms() == numberOfRooms)
                .collect(Collectors.toList());
    }

    public List<Apartment> filterByArea(double minArea, double maxArea) {
        return apartments.stream()
                .filter(apartment -> apartment.getArea() >= minArea && apartment.getArea() <= maxArea)
                .collect(Collectors.toList());
    }

    public List<Apartment> getApartments() {
        return apartments;
    }
}

// Пример использования
class Main {
    public static void main(String[] args) {
        ApartmentManager manager = new ApartmentManager();
        manager.addApartment(new Apartment(1, "101", 45.5, 1, 2, "Main St", "Brick", 20));
        manager.addApartment(new Apartment(2, "202", 60.0, 2, 3, "Main St", "Brick", 15));
        manager.addApartment(new Apartment(3, "303", 30.0, 3, 1, "Second St", "Panel", 25));

        System.out.println("Все квартиры:");
        for (Apartment apartment : manager.getApartments()) {
            System.out.println(apartment);
        }

        System.out.println("\nКвартиры с 2 комнатами:");
        for (Apartment apartment : manager.filterByRooms(2)) {
            System.out.println(apartment);
        }

        System.out.println("\nКвартиры с площадью от 30 до 50 квадратных метров:");
        for (Apartment apartment : manager.filterByArea(30, 50)) {
            System.out.println(apartment);
        }

        // Удаление квартиры
        manager.removeApartment(2);
        System.out.println("\nКвартиры после удаления квартиры с ID 2:");
        for (Apartment apartment : manager.getApartments()) {
            System.out.println(apartment);
        }
    }
}
