# Kafka and Schema Registry

## Avro format
It is a serializable format, like Json, Protobuf or xml.

The serializable format is defined by schema, that is written in Json. 

This format is compressed, serialized, and fully typed by the schema. Like protobuf, is not "printable", different than json format.

- Confluent Schema registry only support it :D

### Avro primitive types
- null: no value
- boolean: binary
- int: 32-bit signed integer
- long: 64-bit signed integer
- float: single precision (32-bit)
- double: double precision (32-bit)
- bytes: sequence of 8-bit unsigned bytes
- string: unicode character

### Complex types
We have also complext types, like enums, arrays, maps, unions and other record types.

A Few thinks about:
- Enums: Changing the enum values after set, break the compatibility
- Unions: Used to define more than one type on a field, if defaults are defined must be of the type of the first item in the union. Common use to optional values: `"type" : ["null", "string"]`.

### Logical type
We have a couple of logical types giving more meaning to primitive with format `"type": "<primitive>", "logicalType": "<logical>"`
- `decimal` with primitive `bytes`: using for exactly float/double values
- `date` with primitive `int`: Number of days since 01/01/1970
- `time-millis` with primitive `long`: number of milliseconds since midnight.
- `timestamps-millis` with primitive `long`: number fo milliseconds since midnight 01/01/1970

### Avro record schema

Common attributes in Json avro schema:
- Name: Name of the schema
- Namespace: Equivalent of package in java
- Doc: Documentation to explain it
- Aliases: Optional other names for your schema
- Fields:
    - Name: Name of the fields
    - Doc: Documentation
    - Type
    - Default

 Customer example:
```json
[
    {
        "type": "record",
        "namespace": "br.com.devcave",
        "name": "Customer",
        "doc": "Avro Schema for our Customer",
        "fields": [
            { "name": "first_name", "type": "string", "doc": "First name of customer"},
            { "name": "middle_name", "type": ["null", "string"], "doc": "Midle name of customer", "default": null},
            { "name": "last_name", "type": "string", "doc": "Last name of customer"},
            { "name": "age", "type": "int", "doc": "Age of customer"},
            { "name": "on_boarding", "type": "int", "logicalType": "date", "doc": ""},
            { "name": "height", "type": "float", "doc": "Height in cms of customer"},
            { "name": "weight", "type": "float", "doc": "Weight in Kgs of customer"},
            { "name": "emails", "type": "array", "items": "string", "default": [], "doc": "Customer emails" },
            { "name": "automated_email", "type": "boolean", "default": true, "doc": "True if the user wants automatic emails"},
            { "name": "address", "type": "br.com.devcave.CustomerAddress" },
            { "name": "created_at", "type": "long", "logicalType": "timestamp-millis", "doc": ""}
        ]
    },
    {
        "type": "record",
        "namespace": "br.com.devcave",
        "name": "CustomerAddress",
        "doc": "Avro Schema for our Customer",
        "fields": [
            { "name": "address", "type": "string", "doc": ""},
            { "name": "city", "type": "string", "doc": ""},
            { "name": "postcode", "type": ["int", "string"], "doc": ""},
            { "name": "type", "type": "enum", "symbols": ["PO BOX","RESIDENTIAL","ENTERPRISE"], "doc": ""}
        ]
    }
]
```

### Schema evolutions
4 type of evolutions:
- Backward: it is when a new schema can be used to read old data. Achieve using default values on new schema.
- Forward: is is when a old schema can be used to read new data. It is not necessary had a default value, but we will ignore this new fields
- Full: Both bacward and forward: Only add or default fields with defaults
- Breaking: No compatible. 
    - Adding / Removing elements from Enum
    - Changing tpye of a field
    - Renaming a required field (without default)

## Running locally
We can use a docker-compose to up a complete environment. You can find it on `docker-compose` folder, file `docker-compose-schema-registry.yml`

### Using confluent tools

```
$ docker run -it --rm --net=host confluentinc/cp-schema-registry:3.3.1 bash
```