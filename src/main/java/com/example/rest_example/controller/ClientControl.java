package com.example.rest_example.controller;

import com.example.rest_example.model.Client;
import com.example.rest_example.repository.RepositoryClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller               //Аннотация @Controller позволяет распознавать класс как компонент, управляемый Spring.
@RequestMapping (path = "/") //сопоставление HTTP-запроса с методом с использованием некоторых основных критериев.
public class ClientControl {
    @Autowired              //Аннотация @Autowired используется для автоматического введения зависимостей указанного типа в текущий компонент.
    private RepositoryClients repositoryClients;

    @PostMapping (path = "/add")
    @ResponseBody                                               //@ResponseBody  аннотация говорит контроллер, возвращаемый объект автоматически сериализуются в JSON и передается обратно в HttpResponse объект.
    public String addNewClient (@RequestBody Client client ) {  // RequestBody аннотации отображает HttpRequest тело к передаче или объекту домена,
                                                                // что позволяет автоматически десериализации въездного HttpRequest тела на объект Java.
        repositoryClients.save(client);
        return "The client is saved";
    }
    @GetMapping(path = "/all_clients")
    @ResponseBody
    public Iterable<Client> getAllClients (){
        return repositoryClients.findAll();

    }


}
