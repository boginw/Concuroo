module Grun
  class ValidationError < StandardError
    def initialize(message, file = nil)
      @message = message
      @file_name = file
    end

    def message
      "Couldn't parse tree:\n\t#{@message.gsub(/line /, file_name+':')}"
    end

    private

    def file_name
      @file_name || 'line'
    end
  end
end