package com.server.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class ApiUsers {


    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private int id;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 6, max = 250)
    private String password;

    public void setPhone(String phone) {

        if (phoneIsValid(phone)){

            this.phone = phone;

        } else {

            throw new RuntimeException("Введен некорректный номер телефона");

        }
    }

    public void setPassword(String password) {

        if (passwordIsValid(password)){

            this.password = password;

        } else {

            throw new RuntimeException("Введен слишком простой пароль");

        }

    }


    /**
     * Метод для валидации номера телефона
     * @param phone номер телефона для проверки
     * @return true - если соответствует требованиям, false - в противном случае
     */
    private boolean phoneIsValid(String phone) {
        return phone.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{10}$");
    }


    /**
     * Метод для валидации пароля
     * пароль должен содержать
     *  - не менее 6 символов
     *  - хотя бы одну строчную латинскую букву
     *  - хотя бы одну заглавную латинскую букву
     *  - хотя бы один спецсимвол
     *  - хотя бы одну цифру от 0 до 9
     * @param password пароль для проверки
     * @return true - если соответствует требованиям, false - в противном случае
     */
    private boolean passwordIsValid(String password) {
        return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,}$");
    }


}
