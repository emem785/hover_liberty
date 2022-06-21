import 'package:pigeon/pigeon.dart';

class HoverRequest {
  String? actionId;
  Map<String?, String?>? extraData;
}

class HoverResponse {
  int? responseCode;
  String? errorMessage;
  List<String?>? messages;
}

@HostApi()
abstract class HoverApi {
  @async
  HoverResponse initiateRequest(HoverRequest request);
}
