# Z2 Exposed

[![Maven Central](https://img.shields.io/maven-central/v/hu.simplexion.z2/z2-exposed-runtime)](https://mvnrepository.com/artifact/hu.simplexion.z2/z2-exposed-runtime)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Integration between [Exposed](https://github.com/JetBrains/Exposed),
[Z2 Service](https://github.com/spxbhuhb/z2-service) and
[Z2 Schematic](https://github.com/spxbhuhb/z2-schematic).

## SchematicUuidTable

* Extends `UUIDTable` from Exposed.
* Use it as any other Exposed table.
* Automatic mapping between schematic instances and the table.
* Column name and schematic field name must equal.
* The `uuid` field in the schematic is mandatory.

```kotlin
class TestSchematic : Schematic<TestSchematic>() {

    var uuid by uuid<TestSchematic>()

    var booleanField by boolean()
    var intField by int()
    var stringField by string()
    var uuidField by uuid<TestSchematic>()

}

class TestTable : SchematicUuidTable<TestSchematic>(
    "test_table",
    { TestSchematic() }
) {

    companion object {
        val accountCredentialsTable = AccountCredentialsTable(principalTable)
    }

    val booleanField = bool("booleanField")
    val intField = integer("intField")
    val stringField = text("stringField")
    val uuidField = uuid("uuidField")

}

class SchematicUuidTableTest {

    @Test
    fun testMapping() {
        h2Test(TestTable)

        val schematic = TestSchematic().apply {
            booleanField = true
            intField = 12
            stringField = "abc"
            uuidField = UUID()
        }

        transaction {
            schematic.uuid = TestTable.insert(schematic)

            val list = TestTable.list()
            
            TestTable.update(schematic.uuid, schematic)

            val value = TestTable.get(schematic.uuid)

            TestTable.remove(schematic.uuid)
        }
    }
}
```

## Registration Helpers

Register tables and implementations with the `tables` and `implementations`.

```kotlin
tables(table1, table2)
implementations(impl1, impl2)
```

* `tables` calls `SchemaUtils.createMissingTablesAndColumns` in an Exposed transaction
* `implemenations` adds the implementations to `defaultServiceImplFactory` with a wrapper that calls service functions in an Exposed transaction

## Testing

Create an in-memory H2 database and add all tables:

```kotlin
 h2Test(table1, table2)
```

## License

> Copyright (c) 2023 Simplexion Kft, Hungary and contributors
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this work except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.