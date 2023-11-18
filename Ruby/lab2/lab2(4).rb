#Variant 9

def decimalToBinary(num)
  x = num.divmod(2)
  arr = []
  i = 0
  while (x[0] > 0) do
      arr[i] = x[1]
      print x, "\n"
      x = x[0].divmod(2)
      i += 1
      if(x[0] == 0) then 
          arr[i] = x[1]
          print x, "\n"
      end
  end
  return arr.reverse.join.to_i
end

number = 444

puts "Converted to Binary:\n\n"
result = decimalToBinary(number)
puts "\nResult: #{result}"
