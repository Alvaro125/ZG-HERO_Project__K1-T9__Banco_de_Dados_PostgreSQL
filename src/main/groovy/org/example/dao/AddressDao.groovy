package org.example.dao

import org.example.entity.AddressEntity

interface AddressDao {

    def create(AddressEntity address)
    AddressEntity getById(Integer id)
    def updateById(AddressEntity address)
    def deleteById(Integer id)

}
