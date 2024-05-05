package org.example.dao

import org.example.entity.AddressEntity

interface AddressDao {

    AddressEntity create(AddressEntity address)
    AddressEntity getById(Integer id)
    void updateById(AddressEntity address)
    void deleteById(Integer id)

}
