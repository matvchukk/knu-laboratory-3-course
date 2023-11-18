#Variant 9

def baseToDecimal(num, base_of_num)
  num_arr = num.digits
  n = num_arr.size - 1
  result = 0.0

  for i in 0..n do
      result += (num_arr[i])*base_of_num**i
  end
  return result
end

number = 100011111001
base = 2
puts "Converted to Decimal:"
puts baseToDecimal(number, base).to_i

