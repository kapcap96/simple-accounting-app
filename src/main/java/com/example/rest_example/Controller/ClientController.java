package com.example.rest_example.Controller;

import com.example.rest_example.model.Client;
import com.example.rest_example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController — говорит спрингу, что данный класс является REST контроллером.
@RestController                                 // Т.е. в данном классе будет реализована логика обработки клиентских запросов
public class ClientController {
    private final ClientService clientService;

                                                //@Autowired говорит спрингу, что в этом месте необходимо внедрить зависимость. В конструктор мы передаем интерфейс ClientService.
                                                // Реализацию данного сервиса мы пометили аннотацией @Service ранее, и теперь спринг сможет передать экземпляр
                                                // этой реализации в конструктор контроллера.
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/clients")                                //@PostMapping(value = "/clients") — здесь мы обозначаем, что данный метод обрабатывает POST запросы на адрес /clients
    public ResponseEntity<?> create(@RequestBody Client client) { //Метод возвращает ResponseEntity<?>. ResponseEntity — специальный класс для возврата ответов. С помощью него мы сможем в дальнейшем вернуть клиенту HTTP статус код.
        clientService.create(client);                            //Внутри тела метода мы вызываем метод create у ранее созданного сервиса и передаем ему принятого в параметрах контроллера клиента.
        return new ResponseEntity<>(HttpStatus.CREATED);        //После чего возвращаем статус 201 Created, создав новый объект ResponseEntity и передав в него нужное значение енума HttpStatus.
    }

    @GetMapping(value = "/clients")                             //@GetMapping(value = "/clients") — все аналогично аннотации @PostMapping, только теперь мы обрабатываем GET запросы.
    public ResponseEntity<List<Client>> read() {                //На этот раз мы возвращаем ResponseEntity<List<Client>>, только в этот раз, помимо HTTP статуса, мы вернем еще и тело ответа, которым будет список клиентов.
        final List<Client> clients = clientService.readALL();

        return clients != null &&  !clients.isEmpty()           //Внутри метода, с помощью нашего сервиса мы получаем список всех клиентов. Далее, в случае если список не null и не пуст, мы возвращаем c помощью класса ResponseEntity сам список клиентов и HTTP статус 200 OK. Иначе мы возвращаем просто HTTP статус 404 Not Found.
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable(name = "id") int id) {
        final Client client = clientService.read(id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Client client) {
        final boolean updated = clientService.update(client, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = clientService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
