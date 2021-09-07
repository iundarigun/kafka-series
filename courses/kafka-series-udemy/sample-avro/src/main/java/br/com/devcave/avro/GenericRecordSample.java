package br.com.devcave.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordSample {
    public static void main(final String[] args) {
        final Schema.Parser parser = new Schema.Parser();
        final Schema schema = parser.parse("""
                {
                    "type": "record",
                    "namespace": "br.com.devcave",
                    "name": "Customer",
                    "doc": "Avro Schema for our Customer",
                    "fields": [
                        { "name": "first_name", "type": "string", "doc": "First name of customer"},
                        { "name": "last_name", "type": "string", "doc": "Last name of customer"},
                        { "name": "age", "type": "int", "doc": "Age of customer"},
                        { "name": "height", "type": "float", "doc": "Height in cms of customer"},
                        { "name": "weight", "type": "float", "doc": "Weight in Kgs of customer"},
                        { "name": "automated_email", "type": "boolean", "default": true, "doc": "True if the user wants automatic emails"}
                    ]
                }
                """);

        final GenericRecordBuilder builder = new GenericRecordBuilder(schema);
        builder.set("first_name", "John");
        builder.set("last_name", "Doe");
        builder.set("age", 25);
        builder.set("height", 170f);
        builder.set("weight", 80.5f);
        builder.set("automated_email", false);
        final GenericData.Record customer = builder.build();

        System.out.println(customer);

        final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);

        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-generic.avro"));
            dataFileWriter.append(customer);
            System.out.println("Written customer-generic.avro");
        } catch (IOException e) {
            System.out.println("Couldn't write file");
            e.printStackTrace();
        }

        final File file = new File("customer-generic.avro");
        final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        GenericRecord customerRead;
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            customerRead = dataFileReader.next();
            System.out.println("Successfully read avro file");
            System.out.println(customerRead.toString());

            // get the data from the generic record
            System.out.println("First name: " + customerRead.get("first_name"));

            // read a non existent field
            System.out.println("Non existent field: " + customerRead.get("not_here"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
