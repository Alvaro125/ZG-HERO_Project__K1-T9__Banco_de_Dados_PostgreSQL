package org.example.dao.impl

import org.example.dao.AddressDao
import org.example.entity.AddressEntity
import org.example.entity.NaturalPersonEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class AddressDaoImpl implements AddressDao{
    private Connection db
    AddressDaoImpl(Connection db){
        this.db = db
    }

    @Override
    AddressEntity create(AddressEntity address) {
        AddressEntity newAddress = address
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlCreate()
            command = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            command.setString(1, address.getCountry())
            command.setString(2, address.getState())
            command.setString(3, address.getCep())
            command.executeUpdate()
            ResultSet generatedKeys = command.getGeneratedKeys()
            if (generatedKeys.next()) {
                newAddress.id = generatedKeys.getInt(1)
            }
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }

        return newAddress
    }
    private String sqlCreate(){
        return "INSERT INTO address (country, state, cep) VALUES (?, ?, ?)"
    }

    @Override
    AddressEntity getById(Integer id) {
        return null
    }

    @Override
    void updateById(AddressEntity address) {
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlUpdate()
            command = db.prepareStatement(sql)
            command.setString(1, address.country);
            command.setString(2, address.state);
            command.setString(3, address.cep);
            command.setInt(4, address.getId());
            command.executeUpdate();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
    }
    private String sqlUpdate(){
        return """
UPDATE address 
SET country = ?, state = ?, cep = ? 
WHERE id = ?;"""
    }

    @Override
    void deleteById(Integer id) {
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlDelete()
            command = db.prepareStatement(sql)
            command.setInt(1, id)
            command.executeUpdate();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
    }
    private String sqlDelete(){
        return """DELETE FROM address WHERE id = ?;"""
    }

    private void closeResultSetAndStatement(ResultSet result, PreparedStatement statement) {
        try {
            if (result != null) {
                result.close()
            }
            if (statement != null) {
                statement.close()
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        }
    }
}
