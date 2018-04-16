require 'open3'

def file_fixture(fixture_name)
  path = File.join('./spec/fixtures', fixture_name)
  if File.exist?(path)
    File.new(path)
  else
    msg = "the directory '%s' does not contain a file named '%s'"
    raise ArgumentError, msg % ['./spec/fixtures', fixture_name]
  end
end

def grun_valid?(fixture, rule='')
  ret = ""
  Open3.popen3(file_piping(fixture) + " | java org.antlr.v4.gui.TestRig Concuroo #{rule}") do |i, o, e, w|
    ret = e.read
  end

  raise Grun::ValidationError.new(ret, fixture.path) unless ret.empty?
  true
end

def grun_tree(fixture, rule='')
  ret = ""
  err = ""
  Open3.popen3(file_piping(fixture) + " | java org.antlr.v4.gui.TestRig Concuroo #{rule} -tree") do |i, o, e, w|
    ret = o.read
    err = e.read
  end

  raise Grun::ValidationError.new(err, fixture.is_a?(File) ? fixture.path : fixture) unless err.empty?
  ret.chomp
end


def grun_open(fixture, rule='', &block)
  ret = ""
  Open3.popen3(file_piping(fixture) + " | java org.antlr.v4.gui.TestRig Concuroo #{rule}") do |i, o, e, w|
    ret = e.read
  end

  raise Grun::ValidationError.new(ret, fixture.path) unless ret.empty?
  true
end

def file_piping(fixture)
  return "cat #{fixture.path}" if fixture.is_a?(File)
  "echo \"#{fixture}\""
end
