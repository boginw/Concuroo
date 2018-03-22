require 'spec_helper'

RSpec.describe 'int parsing' do
  it 'returns okay in parsing' do
    expect(
      grun_valid?(file_fixture('some_test.co'), 'declarationList')
    ).to be_truthy
  end
  it 'returns tree in parsing' do
    expect(
      grun_tree(file_fixture('some_test.co'), 'declarationList')
    ).to start_with '(declarationList'
  end
  it 'returns tree with const in parsing' do
    expect(
      grun_tree('const int x = 0;', 'declarationList')
    ).to start_with '(declarationList ('
  end
end
