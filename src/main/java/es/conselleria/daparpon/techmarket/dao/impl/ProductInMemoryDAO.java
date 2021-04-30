package es.conselleria.daparpon.techmarket.dao.impl;

import es.conselleria.daparpon.techmarket.dao.CompleteCrudDAO;
import es.conselleria.daparpon.techmarket.model.Product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductInMemoryDAO implements CompleteCrudDAO<Product> {

    private final List<Product> vProducts;

    public ProductInMemoryDAO() {
        this.vProducts = new ArrayList<>();

        AtomicInteger id = new AtomicInteger(1);

        this.vProducts.add(new Product(id.getAndIncrement(), "CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SEXTA GEN", 238.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "PLACA MSI Z17A GAMING PRO DD4", 230.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "MEMORIA 8GB DD4 BUS 2133", 60.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "DISCO D 1TB SATA III SEAGATE 64 MB 72RPM", 52.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "GRABADOR DVD 24X ASUS NEGRO", 17.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "VIDEO MSI NVIDIA GTX970 4GB GDDR5 256BITS", 430.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "VIDEO MSI NVIDIA GTX980 4GB GDDR5 256BITS", 504.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "CASE CORSAIR 500R MT BLACK", 125.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "FUENTE 750W CORSAIR CX750W 80+BRONZ", 99.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "TECLADO Y MOUSE LOGITECH MK120 USB SP", 10.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "ESTABILIZADOR FORZA 8 AVR 220V FVR-1202", 13.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "MONITOR 27 LED ASUS IPS MX279H FH 5M FULL HD", 320.0, 100, 8.7));
        this.vProducts.add(new Product(id.getAndIncrement(), "DISCO SOLIDO SSD 240GB A-DATA SP550 2.5 SATA 6GB", 96.0, 100, 8.7));
    }

    @Override
    public Collection<Product> getAll() {
        return vProducts;
    }

    @Override
    public boolean save(Product p) {
        if (numElementos() == 0) {
            return vProducts.add(p);
        } else {
            int i = 0;
            for (; i < numElementos(); i++) {
                if (vProducts.get(i).equals(p)) {
                    break;
                }
            }
            if (i == numElementos()) {
                boolean added = vProducts.add(p);
                ordenar();
                return added;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean update(Product product) {
        for (int index = 0; index < vProducts.size(); index++) {
            if (vProducts.get(index).getProductId().equals(product.getProductId())) {
                vProducts.set(index, product);
                return true;
            }
        }
        return false;
    }

    private int numElementos() {
        return vProducts.size();
    }

    private void ordenar() {
        Product aux;
        int j;
        for (int i = 1; i < numElementos(); i++) {
            aux = vProducts.get(i);
            j = i - 1;
            while (j >= 0 && vProducts.get(j).getProductId() > aux.getProductId()) {
                vProducts.set(j + 1, vProducts.get(j));
                j--;
            }
            vProducts.set(j + 1, aux);
        }
    }

    @Override
    public Product findById(int identifier) {
        ordenar();

        int inicio = 0;
        int fin = numElementos() - 1;
        int pos;
        while (inicio <= fin) {
            pos = (inicio + fin) / 2;
            if (vProducts.get(pos).getProductId() == identifier) {
                return vProducts.get(pos);
            } else if (vProducts.get(pos).getProductId() < identifier) {
                inicio = pos + 1;
            } else {
                fin = pos - 1;
            }
        }
        return null;
    }

    @Override
    public Collection<Product> buscarNombre(String description) {
        return vProducts.stream()
                .filter(p -> p.getDescription().toUpperCase().contains(description.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Product product) {
        return vProducts.removeIf(p -> p.getProductId().equals(product.getProductId()));
    }
}
