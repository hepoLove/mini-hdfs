// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcServer.proto

package com.hepo.dfs.namenode.rpc.service;

public final class NameNodeServer {
  private NameNodeServer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\027NameNodeRpcServer.proto\022\031com.hepo.dfs." +
      "namenode.rpc\032\026NameNodeRpcModel.proto2\336\001\n" +
      "\017NameNodeService\022c\n\010register\022*.com.hepo." +
      "dfs.namenode.rpc.RegisterRequest\032+.com.h" +
      "epo.dfs.namenode.rpc.RegisterResponse\022f\n" +
      "\theartbeat\022+.com.hepo.dfs.namenode.rpc.H" +
      "eartbeatRequest\032,.com.hepo.dfs.namenode." +
      "rpc.HeartbeatResponseB5\n!com.hepo.dfs.na" +
      "menode.rpc.serviceB\016NameNodeServerP\001b\006pr" +
      "oto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.hepo.dfs.namenode.rpc.model.NameNodeRpcModel.getDescriptor(),
        });
    com.hepo.dfs.namenode.rpc.model.NameNodeRpcModel.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}