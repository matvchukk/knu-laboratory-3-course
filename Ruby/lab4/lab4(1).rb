#Variant 9

def get_array_with_indexes(arr_input)
  arr_zero = arr_input.each_index.select { |index| arr_input[index] == 0 }

  arr_negative = arr_input.each_index.select { |index| arr_input[index] < 0 }

  arr_positive = arr_input.each_index.select { |index| arr_input[index] > 0 }
  
  arr_output = arr_zero + arr_negative + arr_positive

  return arr_output
end

arr_input = [0, 2, 3, 0, 5, 0, 7, 8, -5, 0]
arr_output = get_array_with_indexes(arr_input)
puts "Output array containing zero indices,
negative elements indices and positive elements indices:"
puts arr_output



    

    










