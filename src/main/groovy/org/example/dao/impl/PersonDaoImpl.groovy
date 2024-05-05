package org.example.dao.impl

import org.example.dao.PersonDao
import org.example.entity.AddressEntity
import org.example.entity.PersonEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class PersonDaoImpl implements PersonDao{
    private Connection db
    PersonDaoImpl(Connection db){
        this.db = db
    }

    @Override
    PersonEntity create(PersonEntity person) {
        PersonEntity newPerson = person
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlCreate()
            command = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            command.setString(1, newPerson.getEmail())
            command.setString(2, newPerson.getName())
            command.setString(3, newPerson.getDescription())
            command.setInt(4, newPerson.address.id)
            command.setString(5, newPerson.getPassword())
            command.executeUpdate()
            ResultSet generatedKeys = command.getGeneratedKeys()
            if (generatedKeys.next()) {
                newPerson.id = generatedKeys.getInt(1)
            }
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }

        return newPerson
    }
    private String sqlCreate(){
        return "INSERT INTO people (email, name, description, address, password) VALUES (?, ?, ?, ?, ?)"
    }

    @Override
    PersonEntity getById(Integer id) {
        return null
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
        return """DELETE FROM people WHERE id = ?;"""
    }

    @Override
    void updateById(PersonEntity person) {
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlUpdate()
            command = db.prepareStatement(sql)
            command.setString(1, person.name);
            command.setString(2, person.description);
            command.setString(3, person.email);
            command.setString(4, person.password);
            command.setInt(5, person.id);
            command.executeUpdate();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
    }
    private String sqlUpdate(){
        return """
UPDATE people 
SET name = ?, description = ?, email = ?, password = ?
WHERE id = ?;"""
    }

    private void closeResultSetAndStatement(ResultSet result, PreparedStatement statement) {
        try {
            if (result != null) {
                result.close()
            }
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        } finally {
            try {
                if (statement != null) {
                    statement.close()
                }
            } catch (SQLException exceção_sql) {
                exceção_sql.printStackTrace()
            }
        }
    }
}
