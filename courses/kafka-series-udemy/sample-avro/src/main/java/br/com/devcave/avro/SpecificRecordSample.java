package br.com.devcave.avro;

import br.com.devcave.Customer;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class SpecificRecordSample {
    public static void main(final String[] args) {
        final Customer.Builder builder = Customer.newBuilder();
        builder.setFirstName("John");
        builder.setLastName("Doe");
        builder.setAge(25);
        builder.setHeight(175f);
        builder.setWeight(85.5f);
        final Customer customer = builder.build();

        System.out.println(customer);

        // write it out to a file
        final DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);

        try (DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
            dataFileWriter.append(customer);
            System.out.println("successfully wrote customer-specific.avro");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read it from a file
        final File file = new File("customer-specific.avro");
        final DatumReader<Customer> datumReader = new SpecificDatumReader<>(Customer.class);
        try (DataFileReader<Customer> dataFileReader = new DataFileReader<>(file, datumReader)) {
            System.out.println("Reading our specific record");

            while (dataFileReader.hasNext()) {
                Customer readCustomer = dataFileReader.next();
                System.out.println(readCustomer.toString());
                System.out.println("First name: " + readCustomer.getFirstName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
