package com.maycon.coursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maycon.coursomc.domain.Adress;
import com.maycon.coursomc.domain.BilletPayment;
import com.maycon.coursomc.domain.CardPayment;
import com.maycon.coursomc.domain.Category;
import com.maycon.coursomc.domain.City;
import com.maycon.coursomc.domain.Client;
import com.maycon.coursomc.domain.Order;
import com.maycon.coursomc.domain.OrderItem;
import com.maycon.coursomc.domain.Payment;
import com.maycon.coursomc.domain.Product;
import com.maycon.coursomc.domain.State;
import com.maycon.coursomc.domain.enums.StatusPayment;
import com.maycon.coursomc.domain.enums.TypeClient;
import com.maycon.coursomc.repositories.AdressRepository;
import com.maycon.coursomc.repositories.CategoryRepository;
import com.maycon.coursomc.repositories.CityRepository;
import com.maycon.coursomc.repositories.ClientRepository;
import com.maycon.coursomc.repositories.OrderItensRepository;
import com.maycon.coursomc.repositories.OrderRepository;
import com.maycon.coursomc.repositories.PaymentRepository;
import com.maycon.coursomc.repositories.ProductRepository;
import com.maycon.coursomc.repositories.StateRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityrepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AdressRepository adressRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderItensRepository orderItensRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");

		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));

		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

		State est1 = new State(null, "Minas Gerais");
		State est2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", est1);
		City c2 = new City(null, "São Paulo", est2);
		City c3 = new City(null, "Campinas", est2);

		est1.getCities().addAll(Arrays.asList(c1));
		est2.getCities().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(est1, est2));
		cityrepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "9949593845", TypeClient.PESSOAFISICA);
		cli1.getPhones().addAll(Arrays.asList("47 3374-0453", "47 9934-9302"));
		
		Adress a1 = new Adress(null, "Rua Flores", "300", "Apto 303", "Jardim", "89238923", cli1, c1);
		Adress a2 = new Adress(null, "Avenida Matos", "105", "Sala 800", "Centro", "89340233", cli1, c2);
		
		cli1.getAdress().addAll(Arrays.asList(a1, a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		adressRepository.saveAll(Arrays.asList(a1, a2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Order ord1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order ord2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, a2);
		
		Payment pay1 = new CardPayment(null, StatusPayment.QUITADO, ord1, 6);
		ord1.setPayment(pay1);
		Payment pay2 = new BilletPayment(null, StatusPayment.PENDENTE, ord2, sdf.parse("20/10/2017 00:00"), null);
		ord2.setPayment(pay2);
		
		cli1.getOrders().addAll(Arrays.asList(ord1, ord2));
		
		orderRepository.saveAll(Arrays.asList(ord1, ord2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
		
		OrderItem ordItem1 = new OrderItem(ord1, p1, 0.00, 1, 2000.00);
		OrderItem ordItem2 = new OrderItem(ord1, p3, 0.00, 2, 80.00);
		OrderItem ordItem3 = new OrderItem(ord2, p2, 100.00, 1, 800.00);
		
		ord1.getOrderItens().addAll(Arrays.asList(ordItem1, ordItem2));
		ord2.getOrderItens().addAll(Arrays.asList(ordItem3));
		
		p1.getOrderItens().addAll(Arrays.asList(ordItem1));
		p2.getOrderItens().addAll(Arrays.asList(ordItem3));
		p3.getOrderItens().addAll(Arrays.asList(ordItem2));
		
		orderItensRepository.saveAll(Arrays.asList(ordItem1, ordItem2, ordItem3));
		

	}

}
