package net.htoomaungthait.buynowdotcom.service.address.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.model.Address;
import net.htoomaungthait.buynowdotcom.service.address.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private AddressRepository addressRepository;

    @Override
    public List<Address> createAddresses(List<Address> addresses) {
        return List.of();
    }

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Address getAddressById(Long addressId) {
        return null;
    }

    @Override
    public Address deleteAddressById(Long addressId) {
        return null;
    }

    @Override
    public Address updateAddressById(Long addressId, Address address) {
        return null;
    }
}
