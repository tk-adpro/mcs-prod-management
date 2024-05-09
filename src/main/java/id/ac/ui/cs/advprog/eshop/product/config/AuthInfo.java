package id.ac.ui.cs.advprog.eshop.product.config;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthInfo {
    private Long id;
    private String email;
    private String username;
    private List<String> authorities;
}