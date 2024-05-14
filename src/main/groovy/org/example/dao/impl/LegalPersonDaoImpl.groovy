package org.example.dao.impl

import org.example.dao.LegalPersonDao
import org.example.dto.LoginDto
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class LegalPersonDaoImpl implements LegalPersonDao{
    private Connection db
    LegalPersonDaoImpl(Connection db){
        this.db = db
    }

    @Override
    List<LegalPersonEntity> getAll() {
        String sql = sqlQueryGetAll()
        ResultSet result = null;
        PreparedStatement command = null
        ArrayList<LegalPersonEntity> list = new ArrayList();
        try {
            command = db.prepareStatement(sql);
            result = command.executeQuery();
            while (result.next()) {
                list.add(createLegalPersonEntity(result))
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return list
    }
    private String sqlQueryGetAll(){
        return """SELECT lp.cnpj,p.email,p.description,p.password,p.name,p.id,
ad.country, ad.state, ad.cep, p.address
FROM legalpeople lp 
INNER JOIN people p 
ON lp."idPerson" = p.id 
INNER JOIN address ad 
ON ad.id = p.address;"""
    }

    @Override
    LegalPersonEntity create(LegalPersonEntity person) {
        LegalPersonEntity newPerson = person
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlCreate()
            command = db.prepareStatement(sql)
            command.setInt(1, newPerson.id)
            command.setString(2, newPerson.cnpj)
            command.executeUpdate()
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return newPerson
    }
    private String sqlCreate(){
        return "INSERT INTO legalpeople (\"idPerson\", cnpj) VALUES (?, ?);"
    }

    @Override
    LegalPersonEntity getById(Integer id) {
        String sql = sqlQueryGetById()
        LegalPersonEntity person = null
        ResultSet result = null;
        PreparedStatement command = null
        try {
            command = db.prepareStatement(sql)
            command.setInt(1, id)
            result = command.executeQuery()
            if (result != null && result.next()) {
                person = createLegalPersonEntity(result)
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return person
    }
    private String sqlQueryGetById(){
        return """SELECT lp.cnpj,p.email,p.description,p.password,p.name,p.id,p.address,
ad.country, ad.state, ad.cep 
FROM legalpeople lp 
INNER JOIN people p 
ON lp."idPerson" = p.id 
INNER JOIN address ad 
ON ad.id = p.address WHERE p.id= ? LIMIT 1;"""
    }

    @Override
    void updateById(LegalPersonEntity person) {
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlUpdate()
            command = db.prepareStatement(sql)
            command.setString(1, person.cnpj);
            command.setInt(2, person.id);
            command.executeUpdate();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
    }
    private String sqlUpdate(){
        "UPDATE legalpeople SET cnpj = ? WHERE \"idPerson\" = ? ;"
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
        return """DELETE FROM legalpeople WHERE idPerson = ?;"""
    }

    @Override
    LegalPersonEntity loginPerson(LoginDto req) {
        String sql = sqlQueryGetByEmailAndPassword()
        NaturalPersonEntity person = null
        ResultSet result = null;
        PreparedStatement command = null
        try {
            command = db.prepareStatement(sql)
            command.setString(1, req.email)
            command.setString(2, req.senha)
            result = command.executeQuery()
            if (result != null && result.next()) {
                person = createNaturalPersonEntity(result)
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return person
    }
    private String sqlQueryGetByEmailAndPassword(){
        return """SELECT lp.cnpj,p.email,p.description,p.password,p.name,p.id,p.address,
ad.country, ad.state, ad.cep 
FROM legalpeople lp 
INNER JOIN people p 
ON lp."idPerson" = p.id 
INNER JOIN address ad 
ON ad.id = p.address WHERE p.email=? AND p.password=? LIMIT 1;"""
    }

    private LegalPersonEntity createLegalPersonEntity(ResultSet result) throws SQLException {
        Integer id = result.getInt("id")
        String email = result.getString("email")
        String name = result.getString("name")
        String password = result.getString("password")
        String description = result.getString("description")
        String cnpj = result.getString("cnpj")
        Integer idAddress = result.getInt("address")
        String country = result.getString("country")
        String cep = result.getString("cep")
        String state = result.getString("state")
        return new LegalPersonEntity(name, email, password, description,
                new AddressEntity(country,state,cep, idAddress),
                cnpj,id,[])
    }

    private void closeResultSetAndStatement(ResultSet result, PreparedStatement statement) {
        try {
            if (result != null) {
                result.close()
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            try {
                if (statement != null) {
                    statement.close()
                }
            } catch (SQLException excecao_sql) {
                excecao_sql.printStackTrace()
            }
        }
    }
}
