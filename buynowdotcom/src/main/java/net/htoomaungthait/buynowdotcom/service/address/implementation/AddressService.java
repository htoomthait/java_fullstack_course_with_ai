package net.htoomaungthait.buynowdotcom.service.address.implementation;

import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.model.Address;
import net.htoomaungthait.buynowdotcom.repository.AddressRepository;
import net.htoomaungthait.buynowdotcom.service.address.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> createAddresses(List<Address> addresses) {
        return addressRepository.saveAll(addresses);
    }

    @Override
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address getAddressById(Long addressId) {
        return queryAddressById(addressId);
    }

    @Override
    public Address deleteAddressById(Long addressId) {
        Address address = queryAddressById(addressId);
        addressRepository.delete(address);

        return address;
    }

    @Override
    public Address updateAddressById(Long addressId, Address address) {
        Address existingAddress = queryAddressById(addressId);

        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setAddressType(address.getAddressType());

        return addressRepository.save(existingAddress);

    }

    private Address queryAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address with given ID: " + addressId + " is not found.", "ADDRESS_004"));
    }
}
