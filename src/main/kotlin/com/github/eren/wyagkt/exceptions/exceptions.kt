package com.github.eren.wyagkt.exceptions

class NotAGitRepositoryException: Exception("Error: not a git repository")
class ObjectNotFoundException(obj: String): Exception("Error: object ${obj} not found")
class UnknownObjectTypeException(obj: String, type: String): Exception("Error: unknown object type $type for $obj")