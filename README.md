# CreditCardService

                                
## Steps to build and run

```
  1.) Move to project directory using command line.
  2.) Then use command  : "mvn clean install"
  3.) Then to run : "mvn spring-boot:run"
```  
## Info of API

```
  1.) At the start of application in-memory DB is initialised by creating corresponding schema.
  2.) Right now DB credentials are in application.properties file and are set to "root" in this file. It can be changed   according to your requirement.
  3.) Luhn card validation is applied over added card number and length check is also there.
  4.) If valid card is inserted then success message will be provided in response.
  5.) If there is some invalid card entry, either username or card number duplication, then error message with respective error code will be sent in api response.
```

## Rest Api Routes

```
   GET : 	    http://localhost:8080/card
   Response : [
    {
        "id": 1,
        "userName": "firstUser",
        "balance": 0.00,
        "cardLimit": 10000,
        "cardNumber": "1111222233334444"
    },
    {
        "id": 2,
        "userName": "SecondUser",
        "balance": 0.00,
        "cardLimit": 20000,
        "cardNumber": "2222333344445555"
    }
]
         
```
```
   POST : 	    http://localhost:8080/card/add
   Example body : {
                "userName": "FirstUser",
                "cardLimit": 10000,
                "cardNumber": "1111222233334444"
                }
   Response : Some message with relevant http status code like : 201(success), 400(data corrupted), 409(conflict in data)
         
```

  
