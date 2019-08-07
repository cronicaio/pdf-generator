package io.cronica.api.pdfgenerator.component.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Class that represents fields and tables in HTML template.
 *
 * @author Dmytro Kohut
 *
 * @version 1.0
 */
@Setter
@Getter
@ToString
public class ItemDTO implements UserType, Serializable {

    private List<FieldDTO> fields;

    private List<TableDTO> tables;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.BINARY};
    }

    @Override
    public Class returnedClass() {
        return ItemDTO.class;
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        if (x == null) {
            return y == null;
        }

        return x.equals(y);
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(
            final ResultSet rs, final String[] names,
            final SharedSessionContractImplementor session, final Object owner)
            throws HibernateException, SQLException {

        final InputStream cellContent = rs.getBinaryStream(names[0]);
        if (cellContent == null) {
            return null;
        }
        try {
            final ObjectInputStream is = new ObjectInputStream(cellContent);
            return is.readObject();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert string into object: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void nullSafeSet(
            final PreparedStatement st, final Object value,
            final int index, final SharedSessionContractImplementor session)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.BINARY);
            return;
        }
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(value);
            st.setObject(index, out.toByteArray(), Types.BINARY);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert object to byte array: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        try {
            // using a serialization to create a deep copy
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
            oos.close();
            bos.close();

            final ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            return new ObjectInputStream(bais).readObject();

        } catch (ClassNotFoundException | IOException ex) {
            throw new HibernateException(ex);
        }
    }

    @Override
    @JsonIgnore // we don't need this information in database
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(
            final Serializable cached, final Object owner)
            throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(
            final Object original, final Object target, final Object owner)
            throws HibernateException {
        return this.deepCopy(original);
    }
}
