class Student < ApplicationRecord
  include Visible

  validates :first_name, presence: true
  validates :middle_name, presence: true
  validates :last_name, presence: true
  validates :group, presence: true
  validates :algebra_score, presence: true, numericality: { greater_than: 1, less_than: 6, only_integer: true }
  validates :geometry_score, presence: true, numericality: { greater_than: 1, less_than: 6, only_integer: true }
  validates :computer_science_score, presence: true, numericality: { greater_than: 1, less_than: 6, only_integer: true }

  def format_short
    "#{first_name[0]}.#{middle_name[0]}. #{last_name}"
  end
end
