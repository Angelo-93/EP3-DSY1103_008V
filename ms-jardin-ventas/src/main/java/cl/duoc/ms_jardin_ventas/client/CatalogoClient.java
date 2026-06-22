package cl.duoc.ms_jardin_ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient(name = "ms-jardin-catalogo", url = "${catalogo.api.url}")
public interface CatalogoClient {

    @GetMapping("/productos/{id}")
    Map<String, Object> getProductoById(@PathVariable("id") Long id);

    // NUEVO: Permite a Ventas enviar la orden de restar stock
    @PutMapping("/productos/{id}/stock")
    void reducirStock(@PathVariable("id") Long id, @RequestParam("cantidad") Integer cantidad);
}