package net.htoomaungthait.buynowdotcom.service.address;

import net.htoomaungthait.buynowdotcom.model.Address;

import java.util.List;

public interface IAddressService {


    List<Address> createAddresses(List<Address> addresses);

    List<Address> getAddressesByUserId(Long userId);

    Address getAddressById(Long addressId);

    Address deleteAddressById(Long addressId);

    Address updateAddressById(Long addressId, Address address);
}
