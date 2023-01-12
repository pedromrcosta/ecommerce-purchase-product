package com.pedrocosta.ecommerceproject.services;

import com.pedrocosta.ecommerceproject.entities.Purchase;
import com.pedrocosta.ecommerceproject.enums.MESSAGES;
import com.pedrocosta.ecommerceproject.enums.PRODUCT_TYPE;
import com.pedrocosta.ecommerceproject.repositories.BaseRepository;
import com.pedrocosta.ecommerceproject.repositories.PurchaseRepository;
import com.pedrocosta.ecommerceproject.utils.IpmaApiRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.pedrocosta.ecommerceproject.enums.MESSAGES.DELIVERY_STATUS_HAS_DELIVERY_IN;

@Service
public class PurchaseService extends BaseService<Purchase> {

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseService(BaseRepository<Purchase> repository) {
        super(repository);
    }

    @Override
    public Purchase save(Purchase entity) {

        deliveryHandler(entity);

        entity.getProducts().forEach(purchaseProduct -> {
            final Double price = productService.get(purchaseProduct.getProduct().getId()).getPrice();
            purchaseProduct.setPurchase(entity);
            purchaseProduct.setUnPrice(price);
            purchaseProduct.setTotalPrice(price * purchaseProduct.getQuantity());
        });

        return super.save(entity);
    }

    public List<Purchase> findAllByPurchaseDateBetween(LocalDate begin, LocalDate end) {
        return purchaseRepository.findAllByPurchaseDateBetween(begin, end);
    }

    public List<Purchase> searchPurchaseByProductType(PRODUCT_TYPE type) {
        return purchaseRepository.findByProductType(type);
    }

    public String getDeliveryStatus(int id) {
        Purchase purchase = repository.findById(id).orElse(null);

        if (purchase == null) {
            return "";
        }

        StringBuilder deliveryStatus = new StringBuilder();
        deliveryStatus.append("Compra com o id: ")
                .append(purchase.getId())
                .append(DELIVERY_STATUS_HAS_DELIVERY_IN)
                .append(purchase.getPurchaseDate())
                .append(" ")
                .append(purchase.getDeliveryObs());

        return deliveryStatus.toString();
    }

    /**
     * deliveryHandler
     * check if day of purchase + 1 is weekend
     * check IPMA Api to check heavy rains
     * set an observation
     * */
    private void deliveryHandler(Purchase entity) {
        final String city = entity.getDeliveryCity();
//        entity.setPurchaseDate(LocalDate.of(2023, 01, 13));
        entity.setPurchaseDate(LocalDate.now());
        int rainProbability;

        try {
            if (city == null || city.isEmpty()) {
                throw new RuntimeException("Purchase need to have a city to deliver");
            }
            rainProbability = (int) Double.parseDouble(IpmaApiRequest.getPrevision(city, entity.getPurchaseDate().getDayOfWeek()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        switch (entity.getPurchaseDate().getDayOfWeek()) {
            case FRIDAY -> {
                entity.setDeliveryDate(entity.getPurchaseDate().plusDays(3));
                entity.setDeliveryObs(MESSAGES.DELIVERY_WEEKEND);
            }
            case SATURDAY -> {
                entity.setDeliveryDate(entity.getPurchaseDate().plusDays(2));
                entity.setDeliveryObs(MESSAGES.DELIVERY_WEEKEND);
            }
            default -> {
                entity.setDeliveryDate(entity.getPurchaseDate().plusDays(1));
                entity.setDeliveryObs(MESSAGES.DELIVERY_NORMAL);
            }
        }


        if (rainProbability >= 50) {
            StringBuilder deliveryBuilder = new StringBuilder();
            if (entity.getDeliveryObs().equals(MESSAGES.DELIVERY_WEEKEND)) {
                deliveryBuilder.append(MESSAGES.DELIVERY_WEEKEND);
            }

            if ( rainProbability >= 90 ) {
                entity.setDeliveryDate(entity.getDeliveryDate().plusDays(4));
                deliveryBuilder.append(MESSAGES.getDeliveryDelay(4, rainProbability));
            } else if ( rainProbability >= 75 ) {
                entity.setDeliveryDate(entity.getDeliveryDate().plusDays(3));
                deliveryBuilder.append(MESSAGES.getDeliveryDelay(3, rainProbability));
            } else if ( rainProbability >= 60) {
                entity.setDeliveryDate(entity.getDeliveryDate().plusDays(2));
                deliveryBuilder.append(MESSAGES.getDeliveryDelay(2, rainProbability));
            } else if ( rainProbability >= 50){
                entity.setDeliveryDate(entity.getDeliveryDate().plusDays(1));
                deliveryBuilder.append(MESSAGES.getDeliveryDelay(1, rainProbability));
            }

            entity.setDeliveryObs(deliveryBuilder.toString());
        }

    }
}
