namespace java ApacheThrift_07.generation

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

//编译生成Person，PersonService和DataException三个Java文件
//thrift -out src/main/java/ --gen java src/main/resources/ApacheThrift_07/data.thrift

struct Person {
    1:optional String name,
    2:optional int age,
    3:optional boolean married
}

exception DataException {
    1:optional String message,
    2:optional String callStack,
    3:optional String date
}

service PersonService {
    Person getPersonByUsername(1: required String username) throws (1:DataException dataExeption),
    void savePerson(1:required Person person) throws (1:DataException dataException)
}