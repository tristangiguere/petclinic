go test -race -covermode=atomic -v -coverpkg=./... -coverprofile=cover.out ./mailer-service/...

# Generate visual report
if [[ "$1" == "visual" ]]
then
  go tool cover -html=cover.out -o cover.html
fi

# Get overall coverage percentage for coverage threshold testing
act=$(go tool cover -func cover.out | grep total | awk '{print substr($3, 1, length($3)-1)}')

cvg=${cvg:=90}

if [ 1 -eq "$(echo "${act} < ${cvg}" | bc)" ]
then
  echo "Code coverage test failed"
  exit 1
fi

echo "Expected minimum coverage $cvg%, got $act%"