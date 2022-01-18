package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            UserService userService = context.getBean(UserService.class);
            userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
            userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
            userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
            userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

            CarService carService = context.getBean(CarService.class);
            carService.add(new Car("BMW", 320));
            carService.add(new Car("Mercedes", 600));
            carService.add(new Car("Volvo", 90));
            carService.add(new Car("LADA", 2105));

            List<User> users = userService.listUsers();
            for (User user : users) {
                System.out.println("Список пользователей");
                System.out.println("Id = " + user.getId());
                System.out.println("First Name = " + user.getFirstName());
                System.out.println("Last Name = " + user.getLastName());
                System.out.println("Email = " + user.getEmail());
                System.out.println();
            }

            List<Car> cars = carService.listCars();
            for (Car car : cars) {
                System.out.println("Список машин");
                System.out.println("Id = " + car.getId());
                System.out.println("Model = " + car.getModel());
                System.out.println("Series = " + car.getSeries());
                System.out.println();
            }

            System.out.println("Список пользователи - машины");
            for (User user : users) {
                int pos = users.size() - user.getId().intValue();
                Car car = cars.get(pos);
                user.setCar(car);
                userService.update(user);
                car.setUser(user);
                carService.update(car);
                System.out.println("User Id = " + user.getId());
                System.out.println("Car Id = " + user.getCar().getId());
                System.out.println();
            }
            System.out.println("Список машины - пользователи");
            for (Car car : cars) {
                System.out.println("Car Id = " + car.getId());
                User user = userService.getUserByCar(car.getModel(), car.getSeries());
                System.out.println("User Id = " + user.getId());
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
