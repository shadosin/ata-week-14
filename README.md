# ATA Week 14

## DynamoDB Scan - Clothing Store
#### Table setup
```bash
aws cloudformation create-stack --stack-name Week14-DynamoDBScan-ClothingStore --template-body file://DynamoDBScan/ClothingStore/template.yaml
aws cloudformation wait stack-create-complete --stack-name Week14-DynamoDBScan-ClothingStore
aws dynamodb batch-write-item --request-items file://DynamoDBScan/ClothingStore/seeddata.json
```

#### Testing
```bash
./gradlew dynamodbscan-clothingstore-test
```

#### Clean-up
```bash
aws cloudformation delete-stack --region us-east-1 --stack-name Week14-DynamoDBScan-ClothingStore
```


## DynamoDB Scan - Grocery
#### Table setup
```bash
aws cloudformation create-stack --stack-name Week14-DynamoDBScan-Grocery --template-body file://DynamoDBScan/Grocery/template.yaml
aws cloudformation wait stack-create-complete --stack-name Week14-DynamoDBScan-Grocery
aws dynamodb batch-write-item --request-items file://DynamoDBScan/Grocery/seeddata.json
```

#### Testing
```bash
./gradlew dynamodbscan-grocery-test
```

#### Clean-up
```bash
aws cloudformation delete-stack --region us-east-1 --stack-name Week14-DynamoDBScan-Grocery
```

## Serialization - Order
#### Table setup
```bash
aws cloudformation create-stack --stack-name Week14-Serialization-Order --template-body file://Serialization/Order/template.yaml
aws cloudformation wait stack-create-complete --stack-name Week14-Serialization-Order
aws dynamodb batch-write-item --request-items file://Serialization/Order/seeddata.json
```

#### Testing
```bash
./gradlew serialization-order-test
```

#### Clean-up
```bash
aws cloudformation delete-stack --region us-east-1 --stack-name Week14-Serialization-Order
```

## DynamoDB Scan - Ice Cream
#### Table setup
```bash
aws cloudformation create-stack --stack-name Week14-DynamoDBScan-IceCream --template-body file://DynamoDBScan/IceCream/template.yaml
aws cloudformation wait stack-create-complete --stack-name Week14-DynamoDBScan-IceCream
aws dynamodb batch-write-item --request-items file://DynamoDBScan/IceCream/seeddata.json
```

#### Testing
```bash
./gradlew dynamodbscan-icecream-test-phase-0
./gradlew dynamodbscan-icecream-test-phase-1
./gradlew dynamodbscan-icecream-test-phase-2
./gradlew dynamodbscan-icecream-test-phase-3
```

#### Before Submitting Run:
```bash
./gradlew dynamodbscan-icecream-all-tests
```

#### Clean-up
```bash
aws cloudformation delete-stack --region us-east-1 --stack-name Week14-DynamoDBScan-IceCream
```

## Queues - Playlist
#### Testing
```bash
./gradlew stacksandqueues-playlist-test
```

## Stacks - Sentence Builder
#### Testing
```bash
./gradlew stacksandqueues-sentencebuilder-test
```

## Stacks And Queues - Palindrome Checker
#### Testing
```bash
./gradlew stacksandqueues-palindromechecker-test
```

## Group Work - Kenzie Java Compiler
#### Testing
```bash
./gradlew balanced-curlybracevalidator-test
./gradlew kenzie-javafile-compiler-test
./gradlew balanced-curlybracevalidator-extension-test
```