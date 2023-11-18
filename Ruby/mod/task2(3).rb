# Variant 3

require 'minitest/autorun'

class TestListPatients < Minitest::Test
  def setup
    @p1 = Patient.new("Smith", "John", "Doe", "123 Main Street", "096782405", "1001", "Headache")
    @p2 = Patient.new("Doe", "Jane", "Doe", "456 Oak Avenue", "0972845822", "987", "Fever")
    @p3 = Patient.new("Johnson", "Bob", "Johnson", "789 Elm Drive", "0963723848", "999", "Cough")
    @arrPatients = [@p1, @p2, @p3]

  end

  def test_search_diagnosis
    result = ListPatients.searchDiagnosis(@arrPatients, "Fever")
    assert_equal [@p2], result
  end

  def test_list_medical_card_in_diapason
    result = ListPatients.listMedicalCardInDiapason(@arrPatients, 990, 1010)
    assert_equal [@p1, @p3], result
  end
end

class Patient
  attr_accessor :id, :surname, :name, :patronymic, :address, :phone, :numOfMedicalCard, :diagnosis
  @@id = 1

  def initialize(surname, name, patronymic, address, phone, numOfMedicalCard, diagnosis)
    @id = @@id
    @@id = @@id + 1
    @surname = surname
    @name = name
    @patronymic = patronymic
    @address = address
    @phone = phone
    @numOfMedicalCard = numOfMedicalCard
    @diagnosis = diagnosis
  end

  def to_s
    @id.to_s + " " + @surname.to_s + " " + @name.to_s + " " + @patronymic.to_s + " " + @address.to_s + " " + @phone.to_s + " " + @numOfMedicalCard.to_s + " " + @diagnosis.to_s
  end
end

class ListPatients
  def self.searchDiagnosis(arrayPatients, diagnosis)
    list = Array.new
    arrayPatients.each { |patient|
      if patient.diagnosis == diagnosis
        list.append(patient)
      end
    }
    list
  end

  def self.listMedicalCardInDiapason(arrPatients, start_interval, end_interval)
    list = Array.new
    arrPatients.each do |patient|
      if start_interval <= patient.numOfMedicalCard.to_i && patient.numOfMedicalCard.to_i <= end_interval

        list.append(patient)
      end
    end
    list
  end
end
