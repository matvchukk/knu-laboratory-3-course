module Visible
  extend ActiveSupport::Concern

  VALID_STATUSES = ['enrolled', 'on academic leave']

  included do
    validates :freshman_state, inclusion: { in: VALID_STATUSES }
  end

  class_methods do
    def enrolled_count
      where(freshman_state: 'enrolled').count
    end

    def academ_leave_count
      where(freshman_state: 'on academic leave').count
    end
  end

  def academ_leave?
    freshman_state == 'on academic leave'
  end
end